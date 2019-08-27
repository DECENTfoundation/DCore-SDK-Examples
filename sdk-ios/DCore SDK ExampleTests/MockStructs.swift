import DCoreKit
import Foundation

// swiftlint:disable all
func decodeMock<T: Decodable>(json: String) -> T {
    return try! JSONDecoder.codingContext().decode(T.self, from: json.data(using: .utf8)!)
}

let dctAssetId = "1.3.0"
let alxAssetId = "1.3.1"

private let assetJSONAlx = """
{"symbol":"ALX","issuer":"1.2.1","id":"\(alxAssetId)","precision":8,"description":"","options":{"is_exchangeable":true,"extensions":[],"core_exchange_rate":{"base":{"amount":"1","asset_id":"\(dctAssetId)"},"quote":{"amount":"2","asset_id":"\(alxAssetId)"}},"max_supply":"7319777577456900"},"dynamic_asset_data_id":"2.3.0"}
"""

let alxAssetMock: Asset = decodeMock(json: assetJSONAlx)
