import DCoreKit
import UIKit
import RxSwift
import RxCocoa

final class TestableViewController: UIViewController {
    let disposeBag = DisposeBag()
    let viewModel = TestableViewModel(assetApi: DCore.restApi.asset)

    @IBOutlet weak var amountField: UITextField!
    @IBOutlet weak var assetIdField: UITextField!
    @IBOutlet weak var resultLabel: UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()

        let convertButton = UIBarButtonItem(title: "Convert", style: .done, target: nil, action: nil)
        self.navigationItem.rightBarButtonItem = convertButton

        convertButton.rx.tap
            .do(onNext: { convertButton.isEnabled = false })
            .flatMap { [weak self] in
                self?.viewModel.convert(
                    amount: self?.amountField.text ?? "",
                    assetId: self?.assetIdField.text ?? ""
                ) ?? Single.just(Result.error("Self is nil"))
            }
            .observeOn(MainScheduler.instance)
            .subscribe(onNext: { [weak self] result in
                convertButton.isEnabled = true
                switch result {
                case .success(let amount): self?.resultLabel.text = "Converted: \(amount)"
                case .error(let message): self?.resultLabel.text = "Unexpected error occurred: \(message)"
                }
            })
            .disposed(by: disposeBag)
    }
}
