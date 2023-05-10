angular.module('angular-js-app').controller('loginController', function ($scope, $http, $localStorage) {

    $scope.tryToAuth = function () {
        $http.post('http://localhost:5555/auth/authenticate', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    let jwt = $localStorage.novemberMarketUser.token;
                    let payload = JSON.parse(atob(jwt.split('.')[1]));
                    $localStorage.videotekaUser =
                        {username: $scope.user.username,
                            token: response.data.token,
                            userId: payload.sub};

                    $scope.userRole = response.data.role;
                    $scope.username = $localStorage.username;
                    $scope.userId = $localStorage.userId;
                    $scope.token = $localStorage.token;

                    alert($scope.user.username + ' is authorized');

                    // $scope.mergeCarts();

                    $scope.user.username = null;
                    $scope.user.password = null;

                    // $location.path('/');
                }
            }, function errorCallback(response) {
            });
    };

    $scope.isUserLoggedIn = function () {
        return !!$localStorage.videotekaUser;
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        // $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.videotekaUser;
        $http.defaults.headers.common.Authorization = '';
        // $rootScope.clearCart();
    };



});