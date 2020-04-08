package com.entropy.dobletetris.Service;

import com.entropy.dobletetris.model.Block;
import com.entropy.dobletetris.model.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PieceFactory {

    private List<String> spriteList =  Arrays.asList("redBlock.jpg", "pastelRedBlock.jpg", "007.jpg", "008.jpg",
            "009.jpg", "010.jpg", "011.jpg", "012.jpg", "013.jpg", "014.jpg",
            "015.jpg", "016.jpg", "017.jpg", "018.jpg", "019.jpg", "020.jpg" );

    private final boolean[][][] iShape = {
            {
                    {false, false, true, false },
                    {false, false, true, false },
                    {false, false, true, false },
                    {false, false, true, false }
            },
            {
                    {false, false, false, false},
                    {false, false, false, false},
                    {true,  true,  true,  true },
                    {false, false, false, false}
            }
    };
    private boolean[][][] cubeShape =  {
            {

                    {false, true,  true,  false, },
                    {false, true,  true,  false, },
                    {false, false, false, false, },
            }
    };

    private final boolean[][][] zShape = {

            {
                    {false, true,  false, },
                    {false, true,  true,  },
                    {false, false, true,  },

            },
            {
                    {false, true,  true,  },
                    {true,  true,  false, },
                    {false, false, false, },
            }
    };
    private final boolean[][][] zReversShape = {
            {
                    {false,  false, true, },
                    {false,  true,  true, },
                    {false,  true,  false,},

            },
            {
                    {true,  true , false, },
                    {false, true,  true,  },
                    {false, false, false, },
            }
    };
    private final boolean[][][] lShape = {
            {
                    {true,  true,  false, },
                    {false, true,  false, },
                    {false, true,  false, },

            },
            {
                    {false, false, false, },
                    {true,  true,  true, },
                    {true,  false, false, },
            },
            {
                    {false, true, false, },
                    {false, true, false, },
                    {false, true, true,  },
            },
            {
                    {false, false, true,  },
                    {true,  true,  true,  },
                    {false, false, false, },
            },

    };
    private final boolean[][][] lRevereShape = {
            {
                    {false, true,  true,  },
                    {false, true,  false, },
                    {false, true,  false  },

            },
            {
                    {true,  false, false, },
                    {true,  true,  true,  },
                    {false, false, false, },
            },
            {
                    {false, true, false,  },
                    {false, true, false,  },
                    {true,  true, false,  },
            },
            {
                    {false, false, false, },
                    {true,  true,  true,  },
                    {false, false, true,  },
            },



    };
    private final boolean[][][] tShape = {
            {
                    {false, true,  false, },
                    {true,  true,  true,  },
                    {false, false, false, },

            },
            {
                    {false, true, false, },
                    {true,  true, false, },
                    {false, true, false, },
            },
            {
                    {false, false, false, },
                    {true,  true,  true, },
                    {false, true,  false,  },
            },
            {
                    {false, true, false, },
                    {false, true, true,  },
                    {false, true, false, },
            },

    };



    private List<boolean[][][]> shapeList = new ArrayList<>();

    private int shapePoolSize;
    private int spritePoolSize;


    public PieceFactory(){

        spritePoolSize = spriteList.size();


        shapeList.add(iShape);
        shapeList.add(zShape);
        shapeList.add(cubeShape);
        shapeList.add(zReversShape);
        shapeList.add(lShape);
        shapeList.add(tShape);
        shapeList.add(lRevereShape);
        shapePoolSize = shapeList.size();

    }
    public Piece createRandomPiece(float startingPositionX, float startingPositionY, int blockSize, List<Block> blocksOnBoard, float gameFieldWidth){

        Random rand = new Random();
        boolean[][][] randomShape = shapeList.get(rand.nextInt(shapePoolSize));
        String randomSprite = spriteList.get(rand.nextInt(spritePoolSize));


        return new Piece( randomShape , randomSprite, startingPositionX, startingPositionY, blockSize, blocksOnBoard, gameFieldWidth);


    }


}
