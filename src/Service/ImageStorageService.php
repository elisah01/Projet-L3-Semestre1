<?php

namespace App\Service;

use Symfony\Component\HttpFoundation\File\UploadedFile;

interface ImageStorageService
{
    /**
     * Upload une image et retourne l’URL publique
     */
    public function upload(UploadedFile $file): string;
}
