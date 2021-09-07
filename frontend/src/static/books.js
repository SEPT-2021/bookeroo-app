// just data to be tested with will use actual db data once that is fixed

// same case here
import image from "../assets/BlueAdventure.png";

const books = [
  {
    title: "Gold Moon",
    description:
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed purus tellus, interdum at nulla eget, rutrum feugiat mi. Curabitur vulputate interdum nibh, eget rhoncus mauris rutrum ut. Nam tincidunt scelerisque libero. Donec ultricies convallis quam, at finibus orci cursus non. Vestibulum sed feugiat est. Nulla facilisi. Duis luctus pharetra justo et bibendum. Quisque et nulla tempus dolor cursus dapibus vel sed lectus. Donec in posuere arcu. Etiam ac sapien ac felis dapibus rutrum nec ut purus. ",
    imageUrl: `${process.env.PUBLIC_URL}/assets/GoldMoon.png`,
    time: 1500,
  },
  {
    title: "Blue Adventure",
    description:
      "Vivamus in libero sed tellus varius tempor vel non ante. Vivamus maximus leo sit amet iaculis fermentum. Suspendisse varius, dui ut mollis feugiat, nulla felis feugiat nisi, maximus sagittis nulla dui vel leo. Nulla ac ante urna. Aliquam erat volutpat. Donec eu tellus tincidunt, sagittis justo eu, congue purus. Phasellus sed facilisis odio. Vivamus tincidunt hendrerit velit, vitae congue massa molestie sed. Phasellus suscipit nisl enim, sit amet euismod risus pellentesque non. Ut consequat augue vel odio hendrerit ultrices eget eu mi. Aenean sapien erat, dignissim nec faucibus ut, fermentum in nulla. Donec velit nisi, placerat sit amet suscipit at, feugiat id ligula. ",
    imageUrl: image,
    time: 1500,
  },
  {
    title: "Black And Beige HoneyBee",
    description:
      "Duis molestie hendrerit tincidunt. Duis eu venenatis ex. Etiam molestie eget erat sed venenatis. Ut ut nulla ut mi sagittis ullamcorper. Curabitur fermentum rhoncus ligula, quis rhoncus neque sodales sit amet. Quisque feugiat eleifend ante, congue fermentum ex vulputate nec. Praesent scelerisque est quam, nec auctor lorem ornare laoreet. Aliquam sit amet sem vel purus efficitur varius et ut leo.",
    imageUrl: `${process.env.PUBLIC_URL}"../assets/Black&BeigeHoneybee.png"`,
    time: 1500,
  },
  {
    title: "Yellow Brown Bee",
    description:
      "Donec vel iaculis tortor, sed pulvinar leo. In blandit dui ex, sed dapibus nibh cursus ac. Morbi viverra diam in feugiat vehicula. Nullam vulputate risus sed mauris maximus, quis sodales ipsum pulvinar. Maecenas iaculis efficitur nisl, sit amet sagittis nulla egestas in. Quisque vel lectus dui. Nunc ante erat, tristique vel tincidunt in, lobortis a enim. Proin nec nunc non tortor fringilla porttitor a eget ipsum. ",
    imageUrl: `${process.env.PUBLIC_URL}/assets/YellowBrownBee.png`,
    time: 1500,
  },
];

export default books;
