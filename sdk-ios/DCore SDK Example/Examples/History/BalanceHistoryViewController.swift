import UIKit
import RxSwift
import RxCocoa
import DCoreKit

final class BalanceHistoryViewController: UITableViewController {
    private let disposeBag = DisposeBag()
    @IBOutlet weak var balanceLabel: UILabel!

    private var numberOfRows: Int {
        return tableView.dataSource?.tableView(tableView, numberOfRowsInSection: 0) ?? 0
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        DCore.restApi.balance.getAllWithAsset(byAccountId: DCore.testAccountId)
            .observeOn(MainScheduler.instance)
            .subscribe(onSuccess: { [weak self] balances in
                self?.balanceLabel.text = "Balances:\n" + balances.reduce("") { output, current in
                    output + "\n" + current.format()
                }
            })
            .disposed(by: disposeBag)

        let limit = 10
        resetPagination(in: tableView)
        paginateControl(in: tableView).do(onNext: { [weak self] _ in
            if let self = self {
                self.setPagination(in: self.tableView, enabled: false)
            }
        }).map { [weak self] _ -> Int in
            (self?.numberOfRows ?? 0) - limit
        }.flatMap { offset in
            DCore.wssApi.history.findAll(
                byAccountId: DCore.testAccountId,
                pagination: .page(bounds: 0..<(0), offset: UInt64(offset + limit), limit: UInt64(limit))
            ).handleError(viewController: self)
        }.observeOn(MainScheduler.instance).scan([BalanceChange]()) { output, items in
            output + items
        }.do(onNext: { [weak self] _ in
            if let self = self {
                self.resetPagination(in: self.tableView)
                self.setPagination(in: self.tableView, enabled: true)
            }
        }).bind(to: tableView.rx.items(
            cellIdentifier: "historyCell", cellType: HistoryCell.self
        )) { (_, element, cell) in
            cell.operationJson.text = String(data: try! JSONEncoder.codingContext().encode(element), encoding: .utf8)
        }.disposed(by: disposeBag)
    }
}

extension BalanceHistoryViewController: SupportsPaginationLoading {}
