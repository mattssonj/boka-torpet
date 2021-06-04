export default function DateFormatter(date) {
  // We expect the format to be of 2021-04-28T19:20:57.457467
  return date.substring(0, 16).replace("T", " ");
}
