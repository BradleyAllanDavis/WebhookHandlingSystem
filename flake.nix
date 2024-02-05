{
  description = "Webhook Handling System";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils, ... }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs {
          inherit system;
          overlays = [];
        };
        jdk = pkgs.jdk17;
      in
      {
        devShell = pkgs.mkShell {
          buildInputs = [
            jdk
            pkgs.maven
          ];

          shellHook = ''
            export JAVA_HOME=${jdk}
          '';
        };
      }
    );
}
