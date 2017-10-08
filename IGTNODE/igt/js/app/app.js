var app = angular.module('todoApp',['ui.router','ngStorage']);

app.constant('urls', {
    BASE: 'http://localhost:8080/todo',
    SERVICE_API : 'http://localhost:8080/todo/t/task/'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                controller:'todoController',
                resolve: {
                    tasks: function ($q, todoService) {
                        console.log('Load All Tasks');
                        var deferred = $q.defer();
                        todoService.loadAllTasks().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);

