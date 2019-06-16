package no.monorepo.app2;

import no.monorepo.lib2.Lib2;


public class App2
{
    public static void main( String[] args )
    {
        System.out.println( "Hello app2!");
        System.out.println(args.length > 0 ? Lib2.capitalize(args[0]) : "NO INPUT");
    }
}
