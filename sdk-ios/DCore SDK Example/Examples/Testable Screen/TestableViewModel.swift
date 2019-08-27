import DCoreKit
import RxSwift
import BigInt
import Foundation

enum Result<T> {
    case success(_ value: T)
    case error(_ message: String)
}

private let dctPrecision = 8

extension Decimal {
    fileprivate static let ten: Decimal = 10
}

struct TestableViewModel {
    let assetApi: AssetApi
}

extension TestableViewModel {
    private func amountToRaw(amount: String) throws -> BigInt {
        guard let amountDecimal = Decimal(string: amount),
            let val = BigInt((amountDecimal * pow(.ten, dctPrecision)).description)
        else { throw NSError(domain: "Conversion failed", code: 0) }
        return val
    }

    func convert(amount: String, assetId: String) -> Single<Result<String>> {
        return self.assetApi.get(byId: assetId)
            .map {
                let converted = try $0.convert(fromDct: self.amountToRaw(amount: amount), rounding: .plain)
                return $0.format(converted.amount)
            }
            .map { Result.success($0) }
            .catchError { Single.just(Result.error(String(describing: $0))) }
    }
}
