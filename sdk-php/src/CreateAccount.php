<?php

require dirname(__DIR__).'/vendor/autoload.php';

use DCorePHP\DCoreApi;
use DCorePHP\Model\ChainObject;

$dcoreApi = new DCoreApi(
//    'https://testnet-api.dcore.io/',
//    'wss://testnet-api.dcore.io'
    'http://stagesocket.decentgo.com:8089/',
    'wss://stagesocket.decentgo.com:8090'
);

// name of our new account
$accountName = 'example-account-' . date('U');

// WIF public key of our new account
// generate and store your own key pair
$publicKey = 'DCT6MA5TQQ6UbMyMaLPmPXE2Syh5G3ZVhv5SbFedqLPqdFChSeqTz';

// WIF private key of an account which registers our new account
// this key corresponds to already existing account
$registrarPrivateKey = '5Jd7zdvxXYNdUfnEXt5XokrE3zwJSs734yQ36a1YaqioRTGGLtn';

// register new account
$dcoreApi->getAccountApi()->registerAccount(
    $accountName,
    $publicKey,
    $publicKey,
    $publicKey,
    new ChainObject('1.2.34'),
    $registrarPrivateKey
);

// retrieve newly created account by name
$account = $dcoreApi->getAccountApi()->getByName($accountName);
var_dump($account);
