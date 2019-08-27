import UIKit
import RxSwift
import RxCocoa
import DCoreKit

final class MessageSendViewController: UIViewController {
    private let disposeBag = DisposeBag()

    @IBOutlet weak var receiver: UITextField!
    @IBOutlet weak var message: UITextField!
    @IBOutlet weak var encryptedSwitch: UISwitch!

    private var sendMessage: Single<TransactionConfirmation> {
        if encryptedSwitch.isOn {
            return DCore.wssApi.messaging.send(
                to: receiver.text ?? "", message: message.text ?? "", credentials: DCore.testCredentials
            )
        } else {
            return DCore.wssApi.messaging.sendUnencrypted(
                to: receiver.text ?? "", message: message.text ?? "", credentials: DCore.testCredentials
            )
        }
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        let sendButton = UIBarButtonItem(title: "Send", style: .done, target: nil, action: nil)
        self.navigationItem.rightBarButtonItem = sendButton

        sendButton.rx.tap.do(onNext: {
            sendButton.isEnabled = false
        }).flatMapLatest { [weak self] _ -> Single<TransactionConfirmation> in
            self?.sendMessage.handleError(viewController: self) ?? Single.never()
        }.observeOn(MainScheduler.instance).subscribe(onNext: { [weak self] _ in
            sendButton.isEnabled = true
            self?.receiver.text = nil
            self?.message.text = nil
            self?.showAlert(title: "Success", message: "Message was sent.")
        }).disposed(by: disposeBag)
    }
}
