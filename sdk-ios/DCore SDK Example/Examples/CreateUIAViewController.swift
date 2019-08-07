import UIKit
import RxSwift
import RxCocoa
import DCoreKit

final class CreateUIAViewController: UIViewController {
    private let disposeBag = DisposeBag()

    @IBOutlet weak var uiaName: UITextField!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let createButton = UIBarButtonItem(title: "Create", style: .done, target: nil, action: nil)
        self.navigationItem.rightBarButtonItem = createButton
        
        createButton.rx.tap.do(onNext: {
            createButton.isEnabled = false
        }).flatMapLatest { [weak self] _ -> Single<TransactionConfirmation> in
            guard let self = self else { return Single.never() }
            return DCore.wssApi.asset.create(
                credentials: DCore.testCredentials,
                symbol: self.uiaName.text ?? "",
                precision: 8,
                description: ""
            ).handleError(viewController: self)
        }.observeOn(MainScheduler.instance).subscribe(onNext: { [weak self] _ in
            createButton.isEnabled = true
            self?.uiaName.text = nil
            self?.showAlert(title: "Success", message: "UIA was created.")
        }).disposed(by: disposeBag)
    }
}
