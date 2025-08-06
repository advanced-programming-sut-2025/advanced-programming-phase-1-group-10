package Common.Models.Config;

import Common.Models.Position;

public class FarmConfig {
    public int lakeHeight, lakeWidth;
    public Position lakeOffset;

    public int houseHeight, houseWidth;
    public Position houseOffset;

    public int quarryHeight, quarryWidth;
    public Position quarryOffset;

    public int greenhouseHeight, greenhouseWidth;
    public Position greenhouseOffset;

    public FarmConfig(Position lakeOffset, int lakeHeight, int lakeWidth,
                      Position houseOffset, int houseHeight, int houseWidth,
                      Position quarryOffset, int quarryHeight, int quarryWidth,
                      Position greenhouseOffset, int greenhouseHeight, int greenhouseWidth) {
        this.lakeOffset = lakeOffset;
        this.lakeHeight = lakeHeight;
        this.lakeWidth = lakeWidth;

        this.houseOffset = houseOffset;
        this.houseHeight = houseHeight;
        this.houseWidth = houseWidth;

        this.quarryOffset = quarryOffset;
        this.quarryHeight = quarryHeight;
        this.quarryWidth = quarryWidth;

        this.greenhouseOffset = greenhouseOffset;
        this.greenhouseHeight = greenhouseHeight;
        this.greenhouseWidth = greenhouseWidth;
    }
}
