angular.module('cart').controller('cartController', function ($scope, $http, $localStorage) {
    $scope.loadCart = function () {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.videotekaGuestCartId)
            .then(function (response) {
                $scope.cart = response.data;
                //console.info($scope.cart);
            });
    };

    // $scope.createOrder = function () {
    //     $http.post('http://localhost:5555/core/api/v1/orders/create',$scope.deliveryadress)
    //         .then(function (response) {
    //             $scope.loadCart();
    //         });
    // }

    $scope.addProductAmount=function (id){
        $http.get('http://localhost:5555/cart/api/v1/cart/'+ $localStorage.videotekaGuestCartId+'/add/'+id)
            .then(function (response){
                $scope.loadCart();
            });
    };

    $scope.subProductAmount=function (id){
        $http.get('http://localhost:5555/cart/api/v1/cart/'+ $localStorage.videotekaGuestCartId+'/sub/'+id)
            .then(function (response){
                $scope.loadCart();
            });
    };

    $scope.removeItem=function (id){
        $http.delete('http://localhost:5555/cart/api/v1/cart/'+ $localStorage.videotekaGuestCartId+'/remove/'+id)
            .then(function (response){
                $scope.loadCart();
            });

    };

    // $scope.guestCreateOrder = function () {
    //     alert('Для оформления заказа необходимо войти в учетную запись');
    // };

    $scope.clearCart = function () {
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.videotekaGuestCartId+'/clear')
            .then(function (response) {
                $scope.cart = response.data;
                $scope.loadCart();
            });
    };

    $scope.loadCart();

});