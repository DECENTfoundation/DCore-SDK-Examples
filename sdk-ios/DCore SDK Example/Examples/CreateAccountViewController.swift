import UIKit
import RxCocoa
import RxSwift
import DCoreKit

final class CreateAccountViewController: UIViewController {
    private let disposeBag = DisposeBag()

    @IBOutlet weak var accountName: UITextField!

    override func viewDidLoad() {
        super.viewDidLoad()

        let createButton = UIBarButtonItem(title: "Create", style: .done, target: nil, action: nil)
        self.navigationItem.rightBarButtonItem = createButton

        createButton.rx.tap
            .do(onNext: {
                assertionFailure("""
                    Creating account from iOS app is highly discouraged. Removing this line will make this example work,
                    but you probably don't want to use this code anyway. If you need to be able to create DCore
                    accounts, consider writing a backend service instead. To create an account, you need a private key
                    of existing account, that will pay transaction fee of the create account operation. It's highly
                    discouraged to include private key of any account into an iOS app binary, or to send private key
                    over network. An attacker might get access to the private key and then he will gain full access
                    to that account.
                """)
            })
            .do(onNext: { createButton.isEnabled = false })
            .map { BrainKey.generate() }
            .flatMapLatest { [weak self] brainKey -> Single<BrainKey> in
                guard let self = self else { return Single.never() }
                return DCore.wssApi.account.create(
                    .withKeyPair(name: self.accountName.text ?? "", keyPair: try! brainKey.asECKeyPair()),
                    registrar: DCore.testCredentials
                )
                    .handleError(viewController: self)
                    .map { _ in brainKey }
            }
            .observeOn(MainScheduler.instance)
            .subscribe(onNext: { [weak self] brainKey in
                createButton.isEnabled = true
                self?.accountName.text = nil
                self?.showAlert(
                    title: "Success",
                    message: """
                    Account was created. Private key: \(try! brainKey.asECKeyPair().description).
                    Brain Key: \(brainKey.words.joined(separator: ","))
                    """
                )
            })
            .disposed(by: disposeBag)
    }
}
