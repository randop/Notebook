import React, { useState, useEffect } from 'react';
import { hot } from 'react-hot-loader/root';
import Button from '@material-ui/core/Button';

// openlayers
import GeoJSON from 'ol/format/GeoJSON'
import Feature from 'ol/Feature';
import MapWrapper from "./components/mapWrapper";

function App() {
  const [ features, setFeatures ] = useState([])

  useEffect( () => {
    fetch('/geo.json')
      .then(response => response.json())
      .then( (fetchedFeatures) => {
        const wktOptions = {
          dataProjection: 'EPSG:4326',
          featureProjection: 'EPSG:4326'
        }
        const parsedFeatures = new GeoJSON().readFeatures(fetchedFeatures, wktOptions)

        // set features into state (which will be passed into OpenLayers
        //  map component as props)
        setFeatures(parsedFeatures)        

      })
  },[])

  return (
      <MapWrapper features={features} />
  );
}

export default hot(App);
