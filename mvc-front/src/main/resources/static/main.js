angular.module('videoteka', ['ngStorage']).controller('mainController', function ($scope, $http, $localStorage) {
    if ($localStorage.videotekaUser) {
        try {
            let jwt = $localStorage.videotekaUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token is expired!!!");
                delete $localStorage.videotekaUser;
                $http.defaults.headers.common.Authorization = '';
            }
        } catch (e) {
        }

        if ($localStorage.videotekaUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marchMarketUser.token;
        }
    }

    $scope.tryToAuth = function () {
        console.log('Привет');
        $http.post('http://localhost:5555/auth/authenticate', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.videotekaUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
    };

    $scope.clearUser = function () {
        delete $localStorage.videotekaUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.videotekaUser) {
            return true;
        } else {
            return false;
        }
    };
    //
    // $scope.loadProducts = function () {
    //     $http.get('http://localhost:8189/market-core/api/v1/products')
    //         .then(function (response) {
    //             $scope.products = response.data;
    //         });
    // };
    //
    // $scope.loadCart = function () {
    //     $http.get('http://localhost:8190/market-cart/api/v1/cart')
    //         .then(function (response) {
    //             $scope.cart = response.data;
    //         });
    // };
    //
    // $scope.addToCart = function (id) {
    //     $http.get('http://localhost:8190/market-cart/api/v1/cart/add/' + id)
    //         .then(function (response) {
    //             $scope.loadCart();
    //         });
    // }
    //
    // $scope.createOrder = function () {
    //
    // }

    // $scope.deleteProduct = function (id) {
    //     $http.delete('http://localhost:8189/market-core/api/v1/products/' + id)
    //         .then(function (response) {
    //             $scope.loadProducts();
    //         });
    // }

    // $scope.createNewProduct = function () {
    //     $http.post('http://localhost:8189/market-core/api/v1/products', $scope.newProduct)
    //         .then(function (response) {
    //             $scope.newProduct = null;
    //             $scope.loadProducts();
    //         });
    // }

    // $scope.loadProducts();
    // $scope.loadCart();
});