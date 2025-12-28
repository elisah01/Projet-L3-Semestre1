FROM php:8.4-apache

# 1. Installer les dépendances système requises
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

# 3. Configurer le DocumentRoot vers /public
ENV APACHE_DOCUMENT_ROOT /var/www/html/public
RUN sed -ri -e 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/sites-available/000-default.conf
RUN sed -ri -e 's!/var/www/!${APACHE_DOCUMENT_ROOT}!g' /etc/apache2/apache2.conf

# 4. Installer Composer
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

WORKDIR /var/www/html
COPY . .

# 5. Variables d’environnement Symfony
ENV APP_ENV=prod
ENV APP_DEBUG=0
ENV APP_RUNTIME_ENV=prod
ENV APP_DOTENV=0

# 6. Installer les dépendances PHP
RUN composer install --no-dev --optimize-autoloader --no-scripts


RUN mkdir -p /var/www/html/var \
&& chown -R www-data:www-data /var/www/html/var

# 8. Apache doit écouter le port Render
RUN sed -i 's/Listen 80/Listen ${PORT}/g' /etc/apache2/ports.conf \
 && sed -i 's/:80/:${PORT}/g' /etc/apache2/sites-available/000-default.conf

EXPOSE ${PORT}
