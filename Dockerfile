# Utiliser PHP 8.2 avec Apache
FROM php:8.2-apache

# 1. Installer les dépendances système requises (Pour Postgres, Intl, Zip)
RUN apt-get update && apt-get install -y \
    libpq-dev \
    libicu-dev \
    libzip-dev \
    unzip \
    git \
    && docker-php-ext-install \
    pdo \
    pdo_pgsql \
    intl \
    zip \
    opcache

# 2. Activer le module de réécriture Apache (Indispensable pour Symfony)
RUN a2enmod rewrite

# 3. Configurer le DocumentRoot Apache vers le dossier /public de Symfony
ENV APACHE_DOCUMENT_ROOT /var/www/html/public
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/sites-available/000-default.conf
RUN sed -ri -e 's!/var/www/!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/apache2.conf

COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

WORKDIR /var/www/html
COPY . .

ENV APP_ENV=prod

# 7. Installer les dépendances PHP (Sans les outils de dev)
RUN composer install --no-dev --optimize-autoloader

# 8. Donner les permissions à Apache sur le dossier var (Cache/Logs)
RUN chown -R www-data:www-data /var/www/html/var

# 9. Exposer le port 80
EXPOSE 80