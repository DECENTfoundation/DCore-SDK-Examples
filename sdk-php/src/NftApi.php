<?php

require dirname(__DIR__).'/vendor/autoload.php';

use DCorePHP\Crypto\Credentials;
use DCorePHP\Crypto\ECKeyPair;
use DCorePHP\DCoreApi;
use DCorePHP\Model\ChainObject;

$dcoreApi = new DCoreApi(
    'https://testnet.dcore.io/',
    'wss://testnet-socket.dcore.io',
    true // @todo set to false in production environment
);

$accountId = new ChainObject('1.2.27');
$account2Id = new ChainObject('1.2.28');

$accountKey = '5JuJbrKZgAATcouJnwpaxPbHMAMDXSgUpQSfxTXzkSUufcnpTUa';
$symbol = 'APPLE' . time() . 'T';

$credentials = new Credentials($accountId, ECKeyPair::fromBase58($accountKey));

// Create NFT Asset
$dcoreApi->getNftApi()->create($credentials, $symbol, 100, false, 'an apple', NftApple::class, true);

// Issue the same NFT Asset
$apple = new NftApple(5, 'red', false);
$notice = $dcoreApi->getNftApi()->issue($credentials, $symbol, $accountId, $apple);
$nftIssuedId = $notice->getTransaction()->getOpResults()[0][1];

// Transfer Issued NFT Asset to another account
$dcoreApi->getNftApi()->transfer($credentials, $account2Id, new ChainObject($nftIssuedId));

// Get NFT Asset by symbol
$nft = $dcoreApi->getNftApi()->getBySymbol($symbol);
$nftId = $nft->getId();

// Get NFT Asset Balances with appropriate class
$balances = $dcoreApi->getNftApi()->getNftBalancesWithClass($account2Id, $nftId, NftApple::class);
var_dump($balances);
