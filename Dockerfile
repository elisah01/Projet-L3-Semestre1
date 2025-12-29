FROM php:8.4-apache

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

RUN a2enmod rewrite

ENV APACHE_DOCUMENT_ROOT /var/www/html/public
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/sites-available/000-default.conf
RUN sed -ri -e 's!/var/www/!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/apache2.conf

COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

WORKDIR /var/www/html
COPY . .

ENV APP_ENV=prod

RUN composer install --no-dev --optimize-autoloader --no-scripts

RUN mkdir -p var && touch .env && chown -R www-data:www-data var .env

RUN sed -i 's/Listen 80/Listen ${PORT}/g' /etc/apache2/ports.conf \
    && sed -i 's/:80/:${PORT}/g' /etc/apache2/sites-available/000-default.conf

ENV PORT=80
EXPOSE ${PORT}