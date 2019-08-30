import RxSwift
import UIKit

extension Single {
    func handleError(viewController: UIViewController?) -> Single<Element> {
        return self.observeOn(MainScheduler.instance).asObservable().catchError { [weak viewController] error in
            viewController?.showAlert(title: "Error", message: "Unexpected error occurred: \(error)")
            return Observable.never() }.asSingle()
    }
}
