FROM node:9.5.0
# setup

#RUN mkdir -p /app
WORKDIR /app

COPY package*.json ./
RUN npm install\
        && npm install tsc -g
COPY . .
RUN npm run build
EXPOSE 3001

USER node
ENV NODE_ENV=production
ENV NODE_PATH=./dist
ENTRYPOINT ["node", "./dist/index.js"]