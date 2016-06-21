package mtg.cardCatalogue.domain;

public class Price {
        private long low;
        private long median;
        private long high;

        public long getLow() {
            return low;
        }

        public void setLow(long low) {
            this.low = low;
        }

        public long getMedian() {
            return median;
        }

        public void setMedian(long median) {
            this.median = median;
        }

        public long getHigh() {
            return high;
        }

        public void setHigh(long high) {
            this.high = high;
        }

    @Override
    public String toString() {
        return String.format("Price[low=%s, median=%s, high=%s]", low, median, high);
    }
}
