

import java.util.ArrayList;

public class Collisions {


    /**
     *
     * @param playerArrayList
     * @param frogArrayList
     * @param map
     */
    protected static void calcPlayerCollisions(ArrayList<Player> playerArrayList, ArrayList<Frog> frogArrayList, Map map){

        Frog jennifer = null;
        Projectile ouchy = null;

        if(playerArrayList!=null){

           for(Player john: playerArrayList){

               if(john!=null&& john.getProjectileArrayList()!=null&&john.getProjectileArrayList().size()>0){

                   if(frogArrayList!=null&&frogArrayList.size()>0){

                       for(int i = 0; i<frogArrayList.size();i++){
                           jennifer = frogArrayList.get(i);

                           if(jennifer!=null){

                               for(int j = 0; j<john.getProjectileArrayList().size();j++){

                                   ouchy = john.getProjectileArrayList().get(j);

                                   if(ouchy!=null){

                                       if(ouchy.isCollision(jennifer,map)){

                                           frogArrayList.get(i).setHealth(jennifer.getHealth()-ouchy.getDamage());

                                           if(frogArrayList.get(i).getHealth()<0){
                                               frogArrayList.remove(i);
                                               i--;

                                               john.addOneKill();
                                           }

                                           john.getProjectileArrayList().remove(j);
                                           j--;

                                           if(i<0)i=0;
                                           if(i>frogArrayList.size())break;

                                           if(j<0)j=0;
                                           if(j>john.getProjectileArrayList().size())break;

                                       }

                                   }

                               }

                           }

                       }


                   }



               }

           }


        }



    }


    protected static void calcFrogPlayerCollisions(ArrayList<Player> playerArrayList , ArrayList<Frog> frogArrayList, Map map) {

        if (playerArrayList != null && frogArrayList != null) {

            for (int i = 0; i < playerArrayList.size(); i++) {

                for(int j = 0 ; j<frogArrayList.size(); j++){

                    if(playerArrayList.get(i).isCollision(frogArrayList.get(j),map)){

                        playerArrayList.get(i).setHealth(
                                playerArrayList.get(i).getHealth()-
                                        (
                                                frogArrayList.get(j).getHealth()*5
                        )
                        )
                        ;
                        frogArrayList.remove(j);
                        j--;
                        if(j<0)j=0;
                        if(j>frogArrayList.size())break;
                    }
                }
            }
        }
    }



    protected static void calcBuildingCollisions( Map gameMap,
                                                 ArrayList<Building> buildingsList, ArrayList<Player> playerList){
        if(buildingsList!=null){
            //Compare each building against the list of moving objects
            //this is going to typically be any dropped items (dropped items do include items on conveyors)
            //additionally this will be the player too
            for(Building building: buildingsList){

                building.calcMovingCollision(playerList.get(0), gameMap);

            }
        }
    }


}
