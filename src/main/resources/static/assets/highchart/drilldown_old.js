/*
 Highcharts JS v6.0.7 (2018-02-16)
 Highcharts Drilldown module

 Author: Torstein Honsi
 License: www.highcharts.com/license

*/
(function(p){"object"===typeof module&&module.exports?module.exports=p:p(Highcharts)})(function(p){(function(e){var p=e.animObject,y=e.noop,z=e.color,A=e.defaultOptions,k=e.each,q=e.extend,E=e.format,F=e.objectEach,t=e.pick,m=e.Chart,n=e.seriesTypes,B=n.pie,n=n.column,u=e.Tick,v=e.fireEvent,C=e.inArray,D=1;q(A.lang,{drillUpText:"\u25c1 Back to {series.name}"});A.drilldown={activeAxisLabelStyle:{cursor:"pointer",color:"#003399",fontWeight:"bold",textDecoration:"underline"},activeDataLabelStyle:{cursor:"pointer",
        color:"#003399",fontWeight:"bold",textDecoration:"underline"},animation:{duration:500},drillUpButton:{position:{align:"right",x:-10,y:10}}};e.SVGRenderer.prototype.Element.prototype.fadeIn=function(a){this.attr({opacity:.1,visibility:"inherit"}).animate({opacity:t(this.newOpacity,1)},a||{duration:250})};m.prototype.addSeriesAsDrilldown=function(a,b){this.addSingleSeriesAsDrilldown(a,b);this.applyDrilldown()};m.prototype.addSingleSeriesAsDrilldown=function(a,b){var c=a.series,d=c.xAxis,h=c.yAxis,f,
    g=[],w=[],l,r,m;m={color:a.color||c.color};this.drilldownLevels||(this.drilldownLevels=[]);l=c.options._levelNumber||0;(r=this.drilldownLevels[this.drilldownLevels.length-1])&&r.levelNumber!==l&&(r=void 0);b=q(q({_ddSeriesId:D++},m),b);f=C(a,c.points);k(c.chart.series,function(a){a.xAxis!==d||a.isDrilling||(a.options._ddSeriesId=a.options._ddSeriesId||D++,a.options._colorIndex=a.userOptions._colorIndex,a.options._levelNumber=a.options._levelNumber||l,r?(g=r.levelSeries,w=r.levelSeriesOptions):(g.push(a),
    w.push(a.options)))});a=q({levelNumber:l,seriesOptions:c.options,levelSeriesOptions:w,levelSeries:g,shapeArgs:a.shapeArgs,bBox:a.graphic?a.graphic.getBBox():{},color:a.isNull?(new e.Color(z)).setOpacity(0).get():z,lowerSeriesOptions:b,pointOptions:c.options.data[f],pointIndex:f,oldExtremes:{xMin:d&&d.userMin,xMax:d&&d.userMax,yMin:h&&h.userMin,yMax:h&&h.userMax},resetZoomButton:this.resetZoomButton},m);this.drilldownLevels.push(a);d&&d.names&&(d.names.length=0);b=a.lowerSeries=this.addSeries(b,!1);
    b.options._levelNumber=l+1;d&&(d.oldPos=d.pos,d.userMin=d.userMax=null,h.userMin=h.userMax=null);c.type===b.type&&(b.animate=b.animateDrilldown||y,b.options.animation=!0)};m.prototype.applyDrilldown=function(){var a=this.drilldownLevels,b;a&&0<a.length&&(b=a[a.length-1].levelNumber,k(this.drilldownLevels,function(a){a.levelNumber===b&&k(a.levelSeries,function(a){a.options&&a.options._levelNumber===b&&a.remove(!1)})}));this.resetZoomButton&&(this.resetZoomButton.hide(),delete this.resetZoomButton);
    this.pointer.reset();this.redraw();this.showDrillUpButton()};m.prototype.getDrilldownBackText=function(){var a=this.drilldownLevels;if(a&&0<a.length)return a=a[a.length-1],a.series=a.seriesOptions,E(this.options.lang.drillUpText,a)};m.prototype.showDrillUpButton=function(){var a=this,b=this.getDrilldownBackText(),c=a.options.drilldown.drillUpButton,d,h;this.drillUpButton?this.drillUpButton.attr({text:b}).align():(h=(d=c.theme)&&d.states,this.drillUpButton=this.renderer.button(b,null,null,function(){a.drillUp()},
    d,h&&h.hover,h&&h.select).addClass("highcharts-drillup-button").attr({align:c.position.align,zIndex:7}).add().align(c.position,!1,c.relativeTo||"plotBox"))};m.prototype.drillUp=function(){if(this.drilldownLevels&&0!==this.drilldownLevels.length){for(var a=this,b=a.drilldownLevels,c=b[b.length-1].levelNumber,d=b.length,h=a.series,f,g,e,l,m=function(d){var b;k(h,function(a){a.options._ddSeriesId===d._ddSeriesId&&(b=a)});b=b||a.addSeries(d,!1);b.type===e.type&&b.animateDrillupTo&&(b.animate=b.animateDrillupTo);
    d===g.seriesOptions&&(l=b)};d--;)if(g=b[d],g.levelNumber===c){b.pop();e=g.lowerSeries;if(!e.chart)for(f=h.length;f--;)if(h[f].options.id===g.lowerSeriesOptions.id&&h[f].options._levelNumber===c+1){e=h[f];break}e.xData=[];k(g.levelSeriesOptions,m);v(a,"drillup",{seriesOptions:g.seriesOptions});l.type===e.type&&(l.drilldownLevel=g,l.options.animation=a.options.drilldown.animation,e.animateDrillupFrom&&e.chart&&e.animateDrillupFrom(g));l.options._levelNumber=c;e.remove(!1);l.xAxis&&(f=g.oldExtremes,
    l.xAxis.setExtremes(f.xMin,f.xMax,!1),l.yAxis.setExtremes(f.yMin,f.yMax,!1));g.resetZoomButton&&(a.resetZoomButton=g.resetZoomButton,a.resetZoomButton.show())}v(a,"drillupall");this.redraw();0===this.drilldownLevels.length?this.drillUpButton=this.drillUpButton.destroy():this.drillUpButton.attr({text:this.getDrilldownBackText()}).align();this.ddDupes.length=[]}};m.prototype.callbacks.push(function(){var a=this;a.drilldown={update:function(b,c){e.merge(!0,a.options.drilldown,b);t(c,!0)&&a.redraw()}}});
    e.addEvent(m.prototype,"beforeShowResetZoom",function(){if(this.drillUpButton)return!1});n.prototype.animateDrillupTo=function(a){if(!a){var b=this,c=b.drilldownLevel;k(this.points,function(a){var b=a.dataLabel;a.graphic&&a.graphic.hide();b&&(b.hidden="hidden"===b.attr("visibility"),b.hidden||(b.hide(),a.connector&&a.connector.hide()))});e.syncTimeout(function(){b.points&&k(b.points,function(a,b){b=b===(c&&c.pointIndex)?"show":"fadeIn";var d="show"===b?!0:void 0,g=a.dataLabel;if(a.graphic)a.graphic[b](d);
        g&&!g.hidden&&(g.fadeIn(),a.connector&&a.connector.fadeIn())})},Math.max(this.chart.options.drilldown.animation.duration-50,0));this.animate=y}};n.prototype.animateDrilldown=function(a){var b=this,c=this.chart.drilldownLevels,d,e=p(this.chart.options.drilldown.animation),f=this.xAxis;a||(k(c,function(a){b.options._ddSeriesId===a.lowerSeriesOptions._ddSeriesId&&(d=a.shapeArgs,d.fill=a.color)}),d.x+=t(f.oldPos,f.pos)-f.pos,k(this.points,function(a){a.shapeArgs.fill=a.color;a.graphic&&a.graphic.attr(d).animate(q(a.shapeArgs,
        {fill:a.color||b.color}),e);a.dataLabel&&a.dataLabel.fadeIn(e)}),this.animate=null)};n.prototype.animateDrillupFrom=function(a){var b=p(this.chart.options.drilldown.animation),c=this.group,d=c!==this.chart.columnGroup,h=this;k(h.trackerGroups,function(a){if(h[a])h[a].on("mouseover")});d&&delete this.group;k(this.points,function(h){var g=h.graphic,f=a.shapeArgs,l=function(){g.destroy();c&&d&&(c=c.destroy())};g&&(delete h.graphic,f.fill=a.color,b.duration?g.animate(f,e.merge(b,{complete:l})):(g.attr(f),
        l()))})};B&&q(B.prototype,{animateDrillupTo:n.prototype.animateDrillupTo,animateDrillupFrom:n.prototype.animateDrillupFrom,animateDrilldown:function(a){var b=this.chart.drilldownLevels[this.chart.drilldownLevels.length-1],c=this.chart.options.drilldown.animation,d=b.shapeArgs,h=d.start,f=(d.end-h)/this.points.length;a||(k(this.points,function(a,k){var g=a.shapeArgs;d.fill=b.color;g.fill=a.color;if(a.graphic)a.graphic.attr(e.merge(d,{start:h+k*f,end:h+(k+1)*f}))[c?"animate":"attr"](g,c)}),this.animate=
            null)}});e.Point.prototype.doDrilldown=function(a,b,c){var d=this.series.chart,e=d.options.drilldown,f=(e.series||[]).length,g;d.ddDupes||(d.ddDupes=[]);for(;f--&&!g;)e.series[f].id===this.drilldown&&-1===C(this.drilldown,d.ddDupes)&&(g=e.series[f],d.ddDupes.push(this.drilldown));v(d,"drilldown",{point:this,seriesOptions:g,category:b,originalEvent:c,points:void 0!==b&&this.series.xAxis.getDDPoints(b).slice(0)},function(b){var c=b.point.series&&b.point.series.chart,d=b.seriesOptions;c&&d&&(a?c.addSingleSeriesAsDrilldown(b.point,
        d):c.addSeriesAsDrilldown(b.point,d))})};e.Axis.prototype.drilldownCategory=function(a,b){F(this.getDDPoints(a),function(c){c&&c.series&&c.series.visible&&c.doDrilldown&&c.doDrilldown(!0,a,b)});this.chart.applyDrilldown()};e.Axis.prototype.getDDPoints=function(a){var b=[];k(this.series,function(c){var d,e=c.xData,f=c.points;for(d=0;d<e.length;d++)if(e[d]===a&&c.options.data[d]&&c.options.data[d].drilldown){b.push(f?f[d]:!0);break}});return b};u.prototype.drillable=function(){var a=this.pos,b=this.label,
        c=this.axis,d="xAxis"===c.coll&&c.getDDPoints,h=d&&c.getDDPoints(a);d&&(b&&h.length?(b.drillable=!0,b.basicStyles||(b.basicStyles=e.merge(b.styles)),b.addClass("highcharts-drilldown-axis-label").css(c.chart.options.drilldown.activeAxisLabelStyle).on("click",function(b){c.drilldownCategory(a,b)})):b&&b.drillable&&(b.styles={},b.css(b.basicStyles),b.on("click",null),b.removeClass("highcharts-drilldown-axis-label")))};e.addEvent(u.prototype,"afterRender",u.prototype.drillable);e.addEvent(e.Point.prototype,
        "afterInit",function(){var a=this,b=a.series,c=b.xAxis,c=c&&c.ticks[a.x];a.drilldown&&e.addEvent(a,"click",function(c){b.xAxis&&!1===b.chart.options.drilldown.allowPointDrilldown?b.xAxis.drilldownCategory(a.x,c):a.doDrilldown(void 0,void 0,c)});c&&c.drillable();return a});e.addEvent(e.Series.prototype,"afterDrawDataLabels",function(){var a=this.chart.options.drilldown.activeDataLabelStyle,b=this.chart.renderer;k(this.points,function(c){var d=c.options.dataLabels,e=t(c.dlOptions,d&&d.style,{});c.drilldown&&
    c.dataLabel&&("contrast"===a.color&&(e.color=b.getContrast(c.color||this.color)),d&&d.color&&(e.color=d.color),c.dataLabel.addClass("highcharts-drilldown-data-label"),c.dataLabel.css(a).css(e))},this)});var x=function(a,b,c){a[c?"addClass":"removeClass"]("highcharts-drilldown-point");a.css({cursor:b})};e.addEvent(e.Series.prototype,"afterDrawTracker",function(){k(this.points,function(a){a.drilldown&&a.graphic&&x(a.graphic,"pointer",!0)})});e.addEvent(e.Point.prototype,"afterSetState",function(){this.drilldown&&
    this.series.halo&&"hover"===this.state?x(this.series.halo,"pointer",!0):this.series.halo&&x(this.series.halo,"auto",!1)})})(p)});