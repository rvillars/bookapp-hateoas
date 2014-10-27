'use strict';

var bookapp = angular.module('bookapp', ['ui.bootstrap','ui.router','ngResource','spring-data-rest']);

bookapp.config(function config($stateProvider, $urlRouterProvider, SpringDataRestInterceptorProvider) {
    $urlRouterProvider.otherwise('/books');
//    SpringDataRestInterceptorProvider.apply();
});

