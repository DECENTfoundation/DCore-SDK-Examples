<?php

require dirname(__DIR__).'/vendor/autoload.php';

use DCorePHP\DCoreApi;
use DCorePHP\Model\ChainObject;

$dcoreApi = new DCoreApi(
    'https://testnet.dcore.io/',
    'wss://testnet-socket.dcore.io',
    true // @todo set to false in production environment
);

// name of our new account
$accountName = 'example-account-' . date('U');

// WIF public key of our new account
// generate and store your own key pair
$publicKey = 'DCT6TjLhr8uESvgtxrbWuXNAN3vcqzBMw5eyEup3PMiD2gnVxeuTb';

// WIF private key of an account which registers our new account
// this key corresponds to already existing account
$registrarPrivateKey = '5Hxwqx6JJUBYWjQNt8DomTNJ6r6YK8wDJym4CMAH1zGctFyQtzt';

// register new account
$dcoreApi->getAccountApi()->registerAccount(
    $accountName,
    $publicKey,
    $publicKey,
    $publicKey,
    new ChainObject('1.2.27'),
    $registrarPrivateKey
);

// retrieve newly created account by name
$account = $dcoreApi->getAccountApi()->getByName($accountName);
var_dump($account);
