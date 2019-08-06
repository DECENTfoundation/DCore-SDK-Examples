import UIKit
import ObjectiveC
import Foundation
import RxSwift
import RxCocoa

private let paginatorSize: CGFloat = 30
private let paginatorThreshold: CGFloat = paginatorSize + 10
private var originInsetsHandle: UInt8 = 0
private var isPaginatingHandle: UInt8 = 0
private var isEnabledPaginationHandle: UInt8 = 0
private var hasPaginatorHandle: UInt8 = 0

private final class LoadingView: UILabel {
    override init(frame: CGRect) {
        super.init(frame: frame)
        text = "Loading..."
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        text = "Loading..."
    }
}

extension UIScrollView {
    fileprivate var paginator: LoadingView? {
        return subviews.first(where: { $0 is LoadingView }) as? LoadingView
    }
    
    fileprivate var originInsets: UIEdgeInsets {
        get { return objc_getAssociatedObject(self, &originInsetsHandle) as? UIEdgeInsets ?? .zero }
        set {
            objc_setAssociatedObject(
                self, &originInsetsHandle, newValue, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC
            )
        }
    }
    
    fileprivate var isPaginating: Bool {
        get { return objc_getAssociatedObject(self, &isPaginatingHandle) as? Bool ?? false }
        set {
            objc_setAssociatedObject(
                self, &isPaginatingHandle, newValue, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC
            )
        }
    }
    
    fileprivate var isEnabledPagination: Bool {
        get { return objc_getAssociatedObject(self, &isEnabledPaginationHandle) as? Bool ?? false }
        set {
            objc_setAssociatedObject(
                self, &isEnabledPaginationHandle, newValue, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC
            )
        }
    }
    
    fileprivate var hasPaginator: Bool {
        get { return objc_getAssociatedObject(self, &hasPaginatorHandle) as? Bool ?? false }
        set {
            objc_setAssociatedObject(
                self, &hasPaginatorHandle, newValue, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC
            )
        }
    }
    
    fileprivate func isPaginationThreshold(_ point: CGPoint, threshold: CGFloat) -> Bool {
        return point.y > (contentSize.height - bounds.height + threshold)
    }
    
    fileprivate var calculatedFrame: CGRect {
        let offsetX = contentSize.width / 2 - paginatorSize / 2
        let offsetY = contentSize.height + originInsets.bottom - paginatorSize / 2 + 2
        return CGRect(x: offsetX, y: offsetY, width: paginatorSize, height: paginatorSize)
    }
}

protocol SupportsPaginationLoading: AnyObject {
    func paginateControl(in scroll: UIScrollView, threshold: CGFloat) -> Observable<Void>
    func isPaginating(in scroll: UIScrollView) -> Bool
    func setPagination(in scroll: UIScrollView, enabled: Bool)
    func resetPagination(in scroll: UIScrollView)
}

extension SupportsPaginationLoading {
    func paginateControl(
        in scroll: UIScrollView, threshold: CGFloat = paginatorThreshold
        ) -> Observable<Void> {
        
        scroll.isEnabledPagination = true
        
        let content = scroll.rx.contentOffset.asObservable()
        let changes = Observable.zip(content, content.skip(1))
        return changes
            .filter { [weak scroll] changed in
                let (old, new) = changed
                guard let view = scroll, old.y > new.y else { return false }
                return !view.isPaginating &&
                    view.isEnabledPagination &&
                    view.isPaginationThreshold(new, threshold: threshold)
            }
            .map { _ in () }
            .do(onNext: { [weak scroll, weak self] in
                guard let view = scroll, !view.hasPaginator && !view.isPaginating else { return }
                self?.addPagination(in: view)
                view.isPaginating = true
                view.contentInset.bottom = view.originInsets.bottom + paginatorSize / 2
            })
            .observeOn(MainScheduler.instance)
    }
    
    func isPaginating(in scroll: UIScrollView) -> Bool {
        return scroll.isPaginating
    }
    
    func setPagination(in scroll: UIScrollView, enabled: Bool) {
        scroll.isEnabledPagination = enabled
        if !enabled {
            resetPagination(in: scroll)
        }
    }
    
    func resetPagination(in scroll: UIScrollView) {
        if let paginator = scroll.paginator {
            paginator.removeFromSuperview()
            scroll.contentInset = scroll.originInsets
            scroll.isPaginating = false
            scroll.hasPaginator = false
        }
    }
    
    private func addPagination(in scroll: UIScrollView) {
        guard !scroll.hasPaginator && !scroll.isPaginating else { return }
        scroll.hasPaginator = true
        scroll.originInsets = scroll.contentInset
        
        let paginator = LoadingView()
        paginator.frame = scroll.calculatedFrame
        scroll.addSubview(paginator)
        scroll.layoutIfNeeded()
    }
}
