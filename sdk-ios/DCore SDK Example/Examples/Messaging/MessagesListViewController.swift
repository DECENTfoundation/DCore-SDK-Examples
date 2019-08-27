import UIKit
import RxSwift
import RxCocoa
import DCoreKit

class MessagesListViewController: UITableViewController {
    private let disposeBag = DisposeBag()

    private var numberOfRows: Int {
        return tableView.dataSource?.tableView(tableView, numberOfRowsInSection: 0) ?? 0
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        getMessages().handleError(viewController: self)
            .observeOn(MainScheduler.instance)
            .asObservable()
            .bind(
                to: tableView.rx.items(cellIdentifier: "messageCell", cellType: MessageCell.self)
            ) { (_, element, cell) in
                cell.from.text = element.sender.description
                cell.to.text = element.receiver.description
                cell.message.text = element.message
            }.disposed(by: disposeBag)
    }

    func getMessages() -> Single<[Message]> {
        return Single.never()
    }
}

extension MessagesListViewController: SupportsPaginationLoading {}

final class MessagesReceivedViewController: MessagesListViewController {
    override var title: String? {
        get { return "Received messages" }
        set(value) {}
    }

    override func getMessages() -> Single<[Message]> {
        return DCore.restApi.messaging.getAllReceiverDecrypted(DCore.testCredentials)
    }
}

final class MessagesSentViewController: MessagesListViewController {
    override var title: String? {
        get { return "Sent messages" }
        set(value) {}
    }

    override func getMessages() -> Single<[Message]> {
        return DCore.restApi.messaging.getAllSenderDecrypted(DCore.testCredentials)
    }
}
