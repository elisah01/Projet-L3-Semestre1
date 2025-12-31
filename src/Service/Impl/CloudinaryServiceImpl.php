<?php

namespace App\Service\Impl;

use App\Service\ImageStorageService;
use Cloudinary\Cloudinary;
use Symfony\Component\HttpFoundation\File\UploadedFile;

class CloudinaryServiceImpl implements ImageStorageService
{
    private Cloudinary $cloudinary;

    public function __construct()
    {
        $this->cloudinary = new Cloudinary($_ENV['CLOUDINARY_URL']);
    }

    public function upload(UploadedFile $file): string
    {
        $result = $this->cloudinary->uploadApi()->upload(
            $file->getRealPath(),
            ['folder' => 'brasil-burger']
        );

        return $result['secure_url'];
    }
}
