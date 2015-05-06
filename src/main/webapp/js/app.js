//set up data for graph
var currentSelection = "";
var from = "";
var to = "";

var nodes;
var links;

//set up SVG for D3
var svg = d3.select('#graph').append('svg').attr('width', 800).attr('height',
		600);

//retrieve data from server and draw graph
function updateRailway() {

	d3.json("/gofetch-railways/api/admin/railway-config", function(error,
			result) {
		if (error) {
			d3.select('#result').append('text').text(error.response);
			return;
		}
		nodes = result.stations;
		links = result.routes;
		redraw();
	});
}

// define arrow markers for graph links
svg.append('svg:defs').append('svg:marker').attr('id', 'end-arrow').attr(
		'viewBox', '0 -5 10 10').attr('refX', 6).attr('markerWidth', 3).attr(
		'markerHeight', 3).attr('orient', 'auto').append('svg:path').attr('d',
		'M0,-5L10,0L0,5').attr('fill', '#A9BCF5');

// redraw railway
function redraw() {
	// handles to link and node element groups
	var path = svg.append('svg:g').selectAll('path'), circle = svg.append(
			'svg:g').selectAll('g');
	// path (link) group
	path = path.data(links);

	// add links
	path.enter().append('svg:path').attr('class', 'link').style('marker-end',
			'url(#end-arrow)');

	path
			.attr(
					'd',
					function(d) {
						var deltaX = d.toStation.x - d.fromStation.x, deltaY = d.toStation.y
								- d.fromStation.y, dist = Math.sqrt(deltaX
								* deltaX + deltaY * deltaY), normX = deltaX
								/ dist, normY = deltaY / dist, sourcePadding = 12, targetPadding = 17, sourceX = d.fromStation.x
								+ (sourcePadding * normX), sourceY = d.fromStation.y
								+ (sourcePadding * normY), targetX = d.toStation.x
								- (targetPadding * normX), targetY = d.toStation.y
								- (targetPadding * normY);
						return 'M' + sourceX + ',' + sourceY + 'L' + targetX
								+ ',' + targetY;
					});

	// show link weight
	path.enter().append('svg:text').attr('class', 'id').attr(
			'x',
			function(d) {
				return Math.abs(d.fromStation.x - d.toStation.x)
						/ 2
						+ 8
						+ (d.fromStation.x < d.toStation.x ? d.fromStation.x
								: d.toStation.x);
			}).attr(
			'y',
			function(d) {
				return Math.abs(d.fromStation.y - d.toStation.y)
						/ 2
						+ 20
						+ (d.fromStation.y < d.toStation.y ? d.fromStation.y
								: d.toStation.y);
			}).text(function(d) {
		return d.distance
	});

	// circle (node) group
	circle = circle.data(nodes, function(d) {
		return d.id;
	});

	// add nodes
	var g = circle.enter().append('svg:g');

	g.append('svg:circle').attr('class', 'node').attr('r', 12).attr("cx",
			function(d) {
				return d.x;
			}).attr("cy", function(d) {
		return d.y;
	}).style('fill', '#FFFF00').style('stroke', function(d) {
		return "#000";
	}).on('mousedown', function(d) {
		// select node
		this.style = 'fill : #2EFE64';
		d3.select('#current-selection').append('text').text(d.id + ' ');
		currentSelection += (d.id + '-');
		if (from == "") {
			from = d.id;
		} else {
			to = d.id;
		}
	});
	// show node IDs
	g.append('svg:text').attr('x', function(d) {
		return d.x;
	}).attr('y', function(d) {
		return d.y + 4;
	}).attr('class', 'id').text(function(d) {
		return d.id;
	});

}

function clearSelection() {
	currentSelection = "";
	d3.select('#current-selection').selectAll("text").remove();
	d3.select('.result-block').selectAll("text").remove();
	from = "";
	to = "";
	redraw();
}

function calculateDistance() {
	d3
			.text("/gofetch-railways/api/passenger/distance?path="
					+ currentSelection, function(error, result) {
				setResults(error, result);
			});
}

function journeyOptions() {
	var stops = val('#jpStops');
	var exact = val('#jpExact');
	d3.text("/gofetch-railways/api/passenger/journey-options?from=" + from
			+ "&to=" + to + "&stops=" + stops + "&exact=" + exact, function(
			error, result) {
		setResults(error, result);
	});
}

function shortestRoute() {
	d3.text("/gofetch-railways/api/passenger/shortest-route?from=" + from
			+ "&to=" + to, function(error, result) {
		setResults(error, result);
	});
}

function setResults(error, result) {
	d3.select('#result').selectAll("text").remove();
	text = error ? error.response : result;
	text.split(";").forEach(function(entry) {
		d3.select('#result').append('text').text(entry).append('br');
	});
}

function val(d) {
	return d3.select(d).property("value");
}

// TODO finish this method
function addStation() {
	var ids = val('#stationAdd');
	var xs = val('#stationAddX');
	var ys = val('#stationAddY');
	$.ajax({
		url : "/gofetch-railways/api/admin/station/add",
		type : 'PUT',
		data : JSON.stringify({
			id : ids,
			x : xs,
			y : ys
		}),
		success : function() {
			updateRailway()
		},
		error : function() {
			alert("Error!")
		}

	});
}

//TODO finish this method
function removeStation() {
	$.ajax({
		url : "/gofetch-railways/api/admin/station/remove/"
				+ val('#stationRemove'),
		type : 'DELETE',
		success : function() {
			svg.selectAll('path').remove(), svg.selectAll('g').remove();
			updateRailway()
		},
		error : function() {
			alert("Error!")
		}

	});
}

//TODO finish this method
function addLink() {
	$.ajax({
		url : '/gofetch-railways/api/admin/route/add',
		type : 'PUT',
		data : JSON.stringify({
			from : val('#linkAddFrom'),
			to : val('#linkAddTo'),
			y : val('#linkAddDistance')
		}),
		success : function() {
			updateRailway()
		},
		error : function() {
			alert("Error!")
		}

	});
}

//TODO finish this method
function removeLink() {
	$.ajax({
		url : "/gofetch-railways/api/admin/route/remove",
		type : 'DELETE',
		data : JSON.stringify({
			from : val('#linkRemoveFrom'),
			to : val('#linkRemoveTo'),
		}),
		success : function() {
			updateRailway()
		},
		error : function() {
			alert("Error!")
		}

	});
}

updateRailway();