package metier;

import java.util.*;

public class PyRat {

    private Set<Point> currentFromages;
    private Set<Path> pathes;
    private Map<Point, List<Point>> myLaby;

    /* Méthode appelée une seule fois permettant d'effectuer des traitements "lourds" afin d'augmenter la performace de la méthode turn. */
    public void preprocessing(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        currentFromages = new HashSet<>(fromages);
        pathes = new HashSet<>();
        populatePathes(laby);
        myLaby = laby;
    }

    /**
     * Remplit la liste unique des points atteignables dans une labyrinthe
     *
     * @param laby - le labyrinthe
     */
    private void populatePathes(Map<Point, List<Point>> laby) {
        for (Point from : laby.keySet())
            for (Point to : laby.keySet())
                //Pour economiser du temps, j'evite de calculer la possibilité de passage entre la même position et
                // entre deux positions différentes qui ont été déjà calculées dans un de deux sens (Ex: A envers B ou B envers A)
                if (!from.equals(to) && (!pathes.contains(new Path(from, to)) || !pathes.contains(new Path(to, from))))
                    if (passagePossible(laby, from, to))
                        pathes.add(new Path(from, to));
    }

    /* Méthode de test appelant les différentes fonctionnalités à développer.
        @param laby - Map<Point, List<Point>> contenant tout le labyrinthe, c'est-à-dire la liste des Points, et les Points en relation (passages existants)
        @param labyWidth, labyHeight - largeur et hauteur du labyrinthe
        @param position - Point contenant la position actuelle du joueur
        @param fromages - List<Point> contenant la liste de tous les Points contenant un fromage. */
    public void turn(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        Point pt1 = new Point(6, 1);
        Point pt2 = new Point(3, 1);
        System.out.println((fromageIci(pt1, fromages) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt1);
        System.out.println((fromageIci_EnOrdreConstant(pt2) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt2);
        System.out.println((passagePossible(laby, pt1, pt2) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt2);
        System.out.println((passagePossible_EnOrdreConstant(pt1, pt2) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt2);
        System.out.println("Liste des points inatteignables depuis la position " + position + " : " + pointsInatteignables(laby, position));
    }

    /* Regarde dans la liste des fromages s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci(Point pos, List<Point> fromages) {
        return fromages.contains(pos);
    }

    /* Regarde de manière performante (accès en ordre constant) s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci_EnOrdreConstant(Point pos) {
        return currentFromages.contains(pos);
    }

    /**
     * Parcous tous les points atteignables du labyrinthe jusqu'à ce qu'il le point voulu à partir d'une position source donnée
     *
     * @param laby    - le labyrinthe
     * @param source  - le point source
     * @param to      - le point voulu
     * @param visited - une liste avec tous les points déjà visités
     * @return true s'il trouve le point voulu, ou false s'il ne le trouve pas
     */
    private boolean innerPassagePossible(Map<Point, List<Point>> laby, Point source, Point to, Set<Point> visited) {
        for (Point point : laby.get(source)) {
            if (!visited.contains(point)) {
                visited.add(point);
                if (point.equals(to))
                    return true;
                else if (innerPassagePossible(laby, point, to, visited))
                    return true;
            }
        }
        return false;
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a ».
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible(Map<Point, List<Point>> laby, Point de, Point a) {
        return laby.get(de) != null && laby.get(de).contains(a);
//        return innerPassagePossible(laby, de, a, new HashSet<>());
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a »,
        mais sans devoir parcourir la liste des Points se trouvant dans la Map !
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible_EnOrdreConstant(Point de, Point a) {
        //mon Set pathes sauvegarde juste un sens de passage entre deux positions donc, je dois verifier les deux directions
        return pathes.contains(new Path(de, a)) || pathes.contains(new Path(a, de));
    }

    /**
     * Cette fonction parcours tous les points atteignables du labyrinthe
     *
     * @param laby    - le labyrinthe
     * @param source  - le point source
     * @param visited - une liste des points déjà visités
     */
    private void parcousAtteignables(Map<Point, List<Point>> laby, Point source, Set<Point> visited) {
        for (Point point : laby.get(source)) {
            if (!visited.contains(point)) {
                visited.add(point);
                parcousAtteignables(laby, point, visited);
            }
        }
    }

//    private void parcousRecursif(Point pos, List<Point> chemin) {
//        chemin.add(pos);
//        for (Point voisin : myLaby.get(pos)) {
//            if (!chemin.contains(pos)) {
//                parcousRecursif(voisin, chemin);
//            }
//        }
//    }

    /* Retourne la liste des points qui ne peuvent pas être atteints depuis la position « pos ».
        @return la liste des points qui ne peuvent pas être atteints depuis la position « pos ». */
    private List<Point> pointsInatteignables(Map<Point, List<Point>> laby, Point pos) {

//        List<Point> inatteignable = new ArrayList<>();
//        List<Point> chemin = new ArrayList<>();
//        parcousRecursif(pos, chemin);
//        for (Point p : myLaby.keySet())
//            if (!chemin.contains(p))
//                inatteignable.add(p);
//        return inatteignable;

        HashSet<Point> visited = new HashSet<>();
        Set<Point> points = laby.keySet();

        parcousAtteignables(laby, pos, visited);

        points.removeAll(visited);
        return (List<Point>) points;
    }
}