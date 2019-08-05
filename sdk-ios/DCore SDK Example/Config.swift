import Foundation
import DCoreKit

private func info(forKey key: String) -> String? {
    return (Bundle.main.infoDictionary?[key] as? String)?
        .replacingOccurrences(of: "\\", with: "")
}

extension DCore {
    static let restUrl: String = info(forKey: "DCORE_NODE_REST_URL") ?? ""
    static let wssUrl: String = info(forKey: "DCORE_NODE_WSS_URL") ?? ""
    static let testAccountId: String = info(forKey: "DCORE_ACCOUNT_ID") ?? ""
    static let testPrivateKey: String = info(forKey: "DCORE_PRIVATE_KEY") ?? ""
    static let testCredentials: Credentials = try! Credentials(
        try! testAccountId.asChainObject(), wif: testPrivateKey
    )
}
