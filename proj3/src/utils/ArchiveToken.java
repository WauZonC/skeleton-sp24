package utils;

import edu.umd.cs.findbugs.annotations.NonNull;
import frame.GameWorld;

public record ArchiveToken(int archiveIndex, long SEED, int[] AvatarCo, int flowerCount, int[][] flowers) {

    /**
     * override method for ArchiveToken instance helping save into file
     */
    @Override
    @NonNull
    public String toString() {
        StringBuilder flower = new StringBuilder(flowerCount + "\n");
        for (int[] flowerCo : flowers) {
            flower.append(flowerCo[0]).append(" ").append(flowerCo[1]).append("\n");
        }
        return String.format("%s %s %s\n", SEED, AvatarCo[0], AvatarCo[1]) + flower;
    }

    /**
     * a static method for loading an archive
     * @param index file index
     * @return a data token stored in class ArchiveToken
     */
    public static ArchiveToken loadArchive(int index) {
        String file = FileUtils.readArchive(index);
        String[] lines = file.split("\n");
        String[] seedAndAvatar = lines[0].split(" ");
        int flowerCnt = Integer.parseInt(lines[1]);
        if (flowerCnt == 0) {
            return new ArchiveToken(index, Long.parseLong(seedAndAvatar[0]),
                    new int[]{Integer.parseInt(seedAndAvatar[1]), Integer.parseInt(seedAndAvatar[2])}, 0, null);
        } else {
            int[][] flowers = new int[flowerCnt][2];
            for (int i = 0; i < flowerCnt; i++) {
                String[] line = lines[i + 2].split(" ");
                flowers[i][0] = Integer.parseInt(line[0]);
                flowers[i][1] = Integer.parseInt(line[1]);
            }
                return new ArchiveToken(index,
                        Long.parseLong(seedAndAvatar[0]),
                        new int[]{Integer.parseInt(seedAndAvatar[1]), Integer.parseInt(seedAndAvatar[2])},
                        flowerCnt,
                        flowers);
        }
    }

    /**
     * a static method for saving an archive using token
     * @param token ArchiveToken
     */
    public static void saveArchive(ArchiveToken token) {
        String file = token.toString();
        for (int i = 1; i <= 3; i++) {
            if (FileUtils.archiveExist(i)) {
                continue;
            }
            FileUtils.writeFile(FileUtils.archive(i), file);
            return;
        }
        FileUtils.writeFile(FileUtils.archive(1), file);
    }

    /**
     * a static helper method for gameworld cast into token
     */
    public static ArchiveToken toToken(GameWorld gameWorld) {
        return new ArchiveToken(0, gameWorld.getWorld().getSeed(),
                gameWorld.getAvatarCo(),
                gameWorld.getCurFlowerCount(),
                gameWorld.getFlowers());
    }
}
