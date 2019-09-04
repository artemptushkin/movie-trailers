## Movie trailers search API

#### How to build

`./mvnw clean install`

#### How to run

`java -jar ./target/movie-trailers-0.0.1-SNAPSHOT.jar`

#### Check run

`curl --request GET --url 'http://localhost:8080/movieTrailers?query=avengers`

#### Client scenario #1

1. On every symbol user enters:<br>
`GET /movieTrailers/suggestion?query=games` -> response with suggestions from the http://www.omdbapi.com
    * Note: `GET /v1/movieTrailers/suggestion?query=game` -> result will be cachable
2. Client shows small movie poster by from image link from the API response `response #poster`
3. User selects movie title by the response data
4. Client keeps `imdbID` from `response #imdbID`
5. Client requests `GET /movieTrailers/tt123ABDA` where tt123ABDA - kept imdbID ->
response movie trailer by the requested `imdbID` and youtube url built by application
properties based `youtube.searchQueryMask` and `youtube.urlMask`
5. Client shows to user movie trailer by the link from the response

#### Client scenario #2

1. Client has input box for year (optional)
2. User enters some finished phrase to search and press enter
3. Client requests `GET /movieTrailers?query=avengers` -> response with pagination movie trailers
4. Client shows several pages with movie trailers (equal to `response #pageCount`)
5. On next page client proceeds request with page `GET /movieTrailers?query=avengers&page=2` and year (optionally)
6. Client shows poster or small video element by the `movieTrailerUrl` from the response
