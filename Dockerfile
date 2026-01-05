FROM php:8.4-apache

# Extensions PHP nécessaires à Symfony + PostgreSQL
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

# Activer mod_rewrite
RUN a2enmod rewrite

# Définir le document root vers public/
ENV APACHE_DOCUMENT_ROOT=/var/www/html/public
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/sites-available/000-default.conf \
    && sed -ri -e 's!/var/www/!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/apache2.conf

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

WORKDIR /var/www/html
COPY . .

# Environnement prod
ENV APP_ENV=prod
ENV APP_DEBUG=0

# Installer les dépendances Symfony
RUN composer install --no-dev --optimize-autoloader

RUN mkdir -p var/cache var/log \
    && chown -R www-data:www-data var

ENV PORT=10000

RUN sed -i "s/Listen 80/Listen ${PORT}/g" /etc/apache2/ports.conf \
    && sed -i "s/:80/:${PORT}/g" /etc/apache2/sites-available/000-default.conf

EXPOSE ${PORT}
