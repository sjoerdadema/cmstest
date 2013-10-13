var cmsmodule = angular.module('kristalcms', []);
cmsmodule.controller('CMSMainCtrl', function($scope, $log, $http) {
	$scope.link_header = "<p>Dit is de header tekst</p>";
	$scope.link_paragraph = "<p>Dit is content die ik kan wijzigen</p>";
	$scope.link_footer = "<p>Dit is de footer tekst</p>";
	
	var cmsconfig = {"config":{"atom.link":[
		              {"@rel":"sections","@href":"http:\/\/localhost:8080\/cmstest\/rest\/config\/sections"},
		              {"@rel":"templates","@href":"http:\/\/localhost:8080\/cmstest\/rest\/config\/templates"},
		              {"@rel":"self","@href":"http:\/\/localhost:8080\/cmstest\/rest\/config"}]}};

	$scope.test = cmsconfig.config['atom.link'];	//.config.atom;	//.link[0]['@rel'];
	
	linkarray = cmsconfig.config['atom.link'];
	$log.info(linkarray);
	
	templateshref = linkarray.filter(function(chain) {
		return chain['@rel'] === "templates";
	})[0]['@href'];
	$log.info(templateshref);
	
	$http({method: 'GET', url: templateshref}).
		success(function(data, status, headers, config) {
			$log.info(data);
			$scope.templates = data;
		}).
		error(function(data, status, headers, config) {
			$log.info(status);
			$scope.templates = data;
		});
	//$log.info(TemplateService.listTemplates());
	
});

var cmspage = angular.module('cmspage', []);
cmspage.controller('PageCtrl', function($scope) {
	$scope.templates = [
	                    { name: 'header.html', url: 'sections/header.html' },
	                    { name: 'main.html', url: 'sections/main.html'},
	                    { name: 'footer.html', url: 'sections/footer.html' }
	                    ];
	
	/*
var initPageRoutes = function(partialurl, templateurl) {
	angular.module('cmspage', []).config(['$routeProvider', function($routeProvider) {
		$routeProvider.
			when(partialurl, {templateUrl: templateurl, controller: HeaderCtrl});
	}]);
};

initPageRoutes("#/header", "target/sections/header.html");
*/
});


/*
cmsmodule.factory("TemplateResource", function($resource) {
	return $resource('rest/config/templates/:id', {}, {
		list: {method: 'GET', params: {}, isArray: true},
		get: {method: 'GET', params: {id: 'id'}},
		create: {method: 'POST'}
	});
});

cmsmodule.factory("TemplateService", function(TemplateResource) {
	return {
		listTemplates: function() {
			return TemplateResource.list();
		},
		getTemplate: function(templateId) {
			return TemplateResource.get(templateId);
		},
		createTemplate: function() {
			return TemplateResource.create();
		}
	};
});
*/