package abdullah_mesut_guler_hw3;


public interface HW3_1Interface {
    public void read_file(String filepath); // read file

    /**
     *
     * @return
     */
    public String find_path(); // find possible path

    /**
     *
     * @param mypath
     */
    public void print_path(String mypath); //print the path to the screen

    /**
     *
     * @param filepath
     */
    public void print_path_to_file(String filepath); //print path to the file
}
