import UIKit
import RxCocoa
import RxSwift
import DCoreKit

final class TransferViewController: UIViewController {
    private let disposeBag = DisposeBag()

    @IBOutlet weak var toAddress: UITextField!
    @IBOutlet weak var amount: UITextField!
    @IBOutlet weak var memo: UITextField!

    override func viewDidLoad() {
        super.viewDidLoad()

        let transferButton = UIBarButtonItem(title: "Transfer", style: .done, target: nil, action: nil)
        self.navigationItem.rightBarButtonItem = transferButton

        transferButton.rx.tap.do(onNext: {
            transferButton.isEnabled = false
        }).flatMapLatest { [weak self] _ -> Single<TransactionConfirmation> in
            guard let self = self else { return Single.never() }
            return DCore.wssApi.account.transfer(
                from: DCore.testCredentials,
                to: self.toAddress.text ?? "",
                amount: AssetAmount(with: self.amount.text ?? ""),
                message: self.memo.text,
                encrypted: false
            ).handleError(viewController: self)
        }.observeOn(MainScheduler.instance).subscribe(onNext: { [weak self] _ in
            transferButton.isEnabled = true
            self?.toAddress.text = nil
            self?.amount.text = nil
            self?.memo.text = nil
            self?.showAlert(title: "Success", message: "Transfer was successful.")
        }).disposed(by: disposeBag)
    }
}
