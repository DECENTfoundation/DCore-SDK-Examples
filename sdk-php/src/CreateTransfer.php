<?php

require dirname(__DIR__).'/vendor/autoload.php';

use DCorePHP\Crypto\Credentials;
use DCorePHP\Crypto\ECKeyPair;
use DCorePHP\DCoreApi;
use DCorePHP\Model\Asset\AssetAmount;
use DCorePHP\Model\ChainObject;
use DCorePHP\Model\OperationHistory;

$dcoreApi = new DCoreApi(
    'https://testnet-api.dcore.io/',
    'wss://testnet-api.dcore.io',
    true // @todo set to false in production environment
);

// prepare transfer transaction input
$senderKeyPair = ECKeyPair::fromBase58('5Hxwqx6JJUBYWjQNt8DomTNJ6r6YK8wDJym4CMAH1zGctFyQtzt');
$sender = new Credentials(new ChainObject('1.2.27'), $senderKeyPair);
$recipientAccountId = '1.2.28';
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
