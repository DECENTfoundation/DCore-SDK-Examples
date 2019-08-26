import XCTest
@testable import DCore_SDK_Example
import RxSwift
import RxBlocking
import Foundation

class TestableViewModelTests: XCTestCase {

    private let assetApi = MockAssetApi()
    private var viewModel: TestableViewModel!

    override func setUp() {
        viewModel = TestableViewModel(assetApi: assetApi)
    }

    func testSuccessfulConversion() {
        assetApi.getAssetByIdResponse = Single.just(alxAssetMock)
        let result = try! viewModel.convert(amount: "3", assetId: alxAssetId).toBlocking().single()
        switch result {
        case .success(let amount): XCTAssertEqual(amount, "6 ALX")
        case .error: XCTAssertTrue(false, "Test shouldn't get here")
        }
    }

    func testFailOnInvalidInput() {
        assetApi.getAssetByIdResponse = Single.just(alxAssetMock)
        let result = try! viewModel.convert(amount: "abc", assetId: alxAssetId).toBlocking().single()
        switch result {
        case .success: XCTAssertTrue(false, "Test shouldn't get here")
        case .error: XCTAssertTrue(true)
        }
    }

    func testFailedConversion() {
        let error = NSError(domain: "Intentional Error", code: 0)
        assetApi.getAssetByIdResponse = Single.error(error)
        let result = try! viewModel.convert(amount: "3", assetId: alxAssetId).toBlocking().single()
        switch result {
        case .success: XCTAssertTrue(false, "Test shouldn't get here")
        case .error(let message): XCTAssertEqual(message, String(describing: error))
        }
    }

}
