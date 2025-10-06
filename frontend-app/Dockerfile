# Dockerfile pour le frontend React
FROM node:22-alpine AS build
WORKDIR /app
COPY package*.json ./
COPY .npmrc ./
RUN npm install
COPY . .
# Accept build argument for API URL
ARG REACT_APP_API_URL
ENV REACT_APP_API_URL=$REACT_APP_API_URL
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
