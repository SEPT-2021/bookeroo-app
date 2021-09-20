FROM node:14

COPY package*.json ./
COPY yarn.lock ./

RUN yarn install

COPY . .

RUN yarn build

EXPOSE 3000

CMD ["npx", "serve", "build", "-p", "3000"]
