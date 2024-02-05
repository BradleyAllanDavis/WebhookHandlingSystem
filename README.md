# Webhook Handling System

## Install and Run
1. Install [Nix package manager](https://determinate.systems/posts/determinate-nix-installer)
   - `curl --proto '=https' --tlsv1.2 -sSf -L https://install.determinate.systems/nix | sh -s -- install`
2. From project directory run `nix develop` to get a Nix dev shell
3. Run `mvn clean install` to build and test application
4. Run `mvn exec:java` to run application
5. Uninstall Nix `/nix/nix-installer uninstall`

## Future TODOs
- Add queue capacity
- Add thread pool
