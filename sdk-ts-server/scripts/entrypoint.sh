#!/bin/bash

rm -rf /api/node_modules
npm install
npm run build
npm start
