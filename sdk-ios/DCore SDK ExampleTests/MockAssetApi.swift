import DCoreKit
import RxSwift

// swiftlint:disable all
final class MockAssetApi: AssetApi {
    var api: DCore.Api { return nil! }

    var getAssetByIdResponse: PrimitiveSequence<SingleTrait, Asset> = Single.never()

    func get(byId id: ChainObjectConvertible) -> PrimitiveSequence<SingleTrait, Asset> {
        return getAssetByIdResponse
    }
}
