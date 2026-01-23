# syntax=docker/dockerfile:1
FROM node:lts AS builder
COPY .npmrc /app/
COPY package*.json /app/
ENV NODE_ENV production
WORKDIR /app
RUN npm ci --omit=dev --loglevel info --no-audit --no-fund
COPY . /app/

FROM node:lts-bookworm-slim
COPY --chown=node:node --from=builder /app /app
WORKDIR /app
EXPOSE 3000
USER node
ENV NODE_ENV production
CMD ["node", "index.js"]
