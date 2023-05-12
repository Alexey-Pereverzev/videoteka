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
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.videotekaUser.token;
        }

        if (!$localStorage.videotekaGuestCartId) {
            $http.get('http://localhost:5555/cart/api/v1/cart/generate')
                .then(function (response) {
                    $localStorage.videotekaGuestCartId = response.data.value;
                    console.log($localStorage.videotekaGuestCartId)
                });
        }
    }

    $scope.tryToAuth = function () {
        $http.post('http://localhost:5555/auth/authenticate', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $scope.payload = JSON.parse(atob(response.data.token.split('.')[1]));
                    $localStorage.videotekaUser = {
                        username: $scope.user.username,
                        token: response.data.token,
                        role: response.data.role,
                        userId: $scope.payload.sub
                    };

                    // console.log(111);
                    // console.log($localStorage.videotekaUser.token);

                    $scope.userRole = response.data.role;

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
            $scope.videotekaUser=$localStorage.videotekaUser.username;
            return true;
        } else {
            return false;
        }
    };

    $scope.addToCart = function () {
        // $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.videotekaGuestCartId + '/add/' + $scope.film)
        //     .then(function (response) {
        //     });
        console.log(idqq);
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