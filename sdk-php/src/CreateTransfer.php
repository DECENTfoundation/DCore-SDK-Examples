<?php

require dirname(__DIR__).'/vendor/autoload.php';

use DCorePHP\Crypto\Credentials;
use DCorePHP\Crypto\ECKeyPair;
use DCorePHP\DCoreApi;
use DCorePHP\Model\Asset\AssetAmount;
use DCorePHP\Model\ChainObject;
use DCorePHP\Model\OperationHistory;

$dcoreApi = new DCoreApi(
//    'https://testnet-api.dcore.io/',
//    'wss://testnet-api.dcore.io'
    'http://stagesocket.decentgo.com:8089/',
    'wss://stagesocket.decentgo.com:8090'
);

// prepare transfer transaction input
$senderKeyPair = ECKeyPair::fromBase58('5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn');
$sender = new Credentials(new ChainObject('1.2.34'), $senderKeyPair);
$recipientAccountId = '1.2.35';
$amountToTransfer = (new AssetAmount())->setAmount(1500000);
$messageToSendWithTransfer = 'Hi, these are Decents from Jon Doe';

// create transfer transaction
$dcoreApi->getAccountApi()->transfer(
    $sender,
    $recipientAccountId,
    $amountToTransfer,
    $messageToSendWithTransfer,
    false
);

// it takes some time to process the transaction
sleep(5);

// retrieve last transaction operation
/** @var OperationHistory[] $operationsactions */
$operations = $dcoreApi->getHistoryApi()->listOperations($sender->getAccount(), '1.7.0', '1.7.0', 1);
$operation = reset($operations);
var_dump($operation);
