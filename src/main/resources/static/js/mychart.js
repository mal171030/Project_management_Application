var chartDataStr = decodeHtml(chartData);
var chartJsonArray = JSON.parse(chartDataStr);

var arrayLength = chartJsonArray.length;
var numericData = [];
var labelData = [];

for (var i = 0; i < arrayLength; i++) {
	numericData[i] = chartJsonArray[i].itemValue;
	labelData[i] = chartJsonArray[i].label;
}


new Chart(document.getElementById("myPieChart"), {
	type: 'pie',
	data: {
		labels: labelData,
		datasets: [{
			label: 'My First Dataset',
			backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f"],
			data: numericData

		}]
	},

	options: {
		plugins: {
			title: {
				display: true,
				text: 'Project Statuses'
			}
		}
	}
});

function decodeHtml(html) {
	var txt = document.createElement("textarea");
	txt.innerHTML = html;
	return txt.textContent;
}

