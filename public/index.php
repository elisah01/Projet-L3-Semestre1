<?php

use App\Kernel;

$_SERVER['APP_ENV'] = $_ENV['APP_ENV'] ?? 'prod';
$_SERVER['APP_DEBUG'] = $_ENV['APP_DEBUG'] ?? '0';

require_once dirname(__DIR__).'/vendor/autoload.php';

$kernel = new Kernel($_SERVER['APP_ENV'], (bool) $_SERVER['APP_DEBUG']);
$kernel->boot();

$response = $kernel->handle(
    Symfony\Component\HttpFoundation\Request::createFromGlobals()
);

$response->send();
$kernel->terminate(
    Symfony\Component\HttpFoundation\Request::createFromGlobals(),
    $response
);
