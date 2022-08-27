// react
import React, { useState, useEffect, useRef } from 'react';

// openlayers
import Map from 'ol/Map'
import View from 'ol/View'
import Feature from 'ol/Feature'
import TileLayer from 'ol/layer/Tile'
import VectorLayer from 'ol/layer/Vector'
import VectorSource from 'ol/source/Vector'
import XYZ from 'ol/source/XYZ'
import {transform} from 'ol/proj'
import {toStringXY} from 'ol/coordinate'
import {fromLonLat} from 'ol/proj';
import {Circle as CircleStyle, Icon, Style, Fill, Stroke } from 'ol/style'

import {defaults as defaultControls} from 'ol/control';

import Point from 'ol/geom/Point'

const stationMarkerStyle = new Style({
  image: new Icon({    
    anchorXUnits: 'fraction',
    anchorYUnits: 'pixels',
    src: 'icons/charging-station.svg',    
    scale: 2,
    anchor: [0.5, 0.5]    
  })
});

const image = new CircleStyle({
  radius: 5,
  fill: null,
  stroke: new Stroke({color: 'red', width: 2}),
  scale: 2.5
});

const styles = {
  'Station': stationMarkerStyle,
  'Point': new Style({
    image: image,
  }),
  'LineString': new Style({
    stroke: new Stroke({
      color: 'black',
      width: 2,
    }),
  }),
  'MultiLineString': new Style({
    stroke: new Stroke({
      color: 'green',
      width: 1,
    }),
  }),
  'MultiPoint': new Style({
    image: image,
  }),
  'MultiPolygon': new Style({
    stroke: new Stroke({
      color: 'yellow',
      width: 1,
    }),
    fill: new Fill({
      color: 'rgba(255, 255, 0, 0.1)',
    }),
  }),
  'Polygon': new Style({
    stroke: new Stroke({
      color: 'blue',
      lineDash: [4],
      width: 3,
    }),
    fill: new Fill({
      color: 'rgba(0, 0, 255, 0.1)',
    }),
  }),
  'GeometryCollection': new Style({
    stroke: new Stroke({
      color: 'magenta',
      width: 2,
    }),
    fill: new Fill({
      color: 'magenta',
    }),
    image: new CircleStyle({
      radius: 10,
      fill: null,
      stroke: new Stroke({
        color: 'magenta',
      }),
    }),
  }),
  'Circle': new Style({
    stroke: new Stroke({
      color: 'red',
      width: 2,
    }),
    fill: new Fill({
      color: 'rgba(255,0,0,0.2)',
    }),
  }),
};

const styleFunction = function (feature) {
  console.log(feature.getGeometry().getType())
  return styles[feature.getGeometry().getType()];
};

function MapWrapper(props) {

  // set intial state
  const [ map, setMap ] = useState()
  const [ featuresLayer, setFeaturesLayer ] = useState()
  const [ selectedCoord , setSelectedCoord ] = useState()

  // pull refs
  const mapElement = useRef()
  
  // create state ref that can be accessed in OpenLayers onclick callback function
  //  https://stackoverflow.com/a/60643670
  const mapRef = useRef()
  mapRef.current = map

  // initialize map on first render - logic formerly put into componentDidMount
  useEffect( () => {

    // create and add vector source layer
    const initalFeaturesLayer = new VectorLayer({
      source: new VectorSource(),
      style: styleFunction,
    })

    // create map
    const initialMap = new Map({
      controls: defaultControls,
      target: mapElement.current,
      layers: [
        
        // Use Openstreetmap
        new TileLayer({
          source: new XYZ({
            url: 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',
            tileSize: 512
          })
        }),

        // Google Maps Terrain
        /* new TileLayer({
          source: new XYZ({
            url: 'http://mt0.google.com/vt/lyrs=p&hl=en&x={x}&y={y}&z={z}',
          })
        }), */

        initalFeaturesLayer
        
      ],
      view: new View({
        projection: 'EPSG:4326',
        center: [-73.935242, 40.730610] /* New York, USA */,
        zoom: 6,
        maxZoom: 18,
        constrainOnlyCenter: true
      }),
      controls: []
    })

    // set map onclick handler
    initialMap.on('click', handleMapClick)

    // save map and vector layer references to state
    setMap(initialMap)
    setFeaturesLayer(initalFeaturesLayer)

  },[])

  // update map if features prop changes - logic formerly put into componentDidUpdate
  useEffect( () => {
    if (props.features.length) { // may be null on first render    
      // set features to map
      featuresLayer.setSource(
        new VectorSource({
          features: props.features // make sure features is an array
        })
      )      
      map.getView().fit(featuresLayer.getSource().getExtent(), {
        padding: [100,100,100,100]
      });      
    }

  },[props.features])

  // map click handler
  const handleMapClick = (event) => {
    const clickedCoord = mapRef.current.getCoordinateFromPixel(event.pixel);
    // set React state
    setSelectedCoord(clickedCoord)
    console.log(clickedCoord)
    
  }

  // render component
  return (      
    <div ref={mapElement} className="map-container"></div>
  ) 

}

export default MapWrapper